#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <netdb.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/event.h>

#define BUF_LEN  256
#define MAX_SOCK 256


/*
typedef struct CLIENT_INFO {
    char hostname[BUF_LEN];
    char ipaddr[BUF_LEN];
    int port;
} CLIENT_INFO;

CLIENT_INFO client_info[MAX_SOCK+1];
*/

int accept_new_client(int sock) {
  socklen_t len;
  int new_socket;
  //struct hostent *peer_host;
  struct sockaddr_in peer_sin;

  len = sizeof(peer_sin);
  new_socket = accept(sock, (struct sockaddr *)&peer_sin, &len);
  if ( new_socket == -1 ){
	perror("accept");
	exit(1);
  }
	/* Debug information
    peer_host = gethostbyaddr((char *)&peer_sin.sin_addr.s_addr,
                              sizeof(peer_sin.sin_addr), AF_INET);
	*/
	/* Host
    strncpy(client_info[new_socket].hostname, peer_host->h_name,
            sizeof client_info[new_socket].hostname);
	*/
	/* IP
    strncpy(client_info[new_socket].ipaddr, inet_ntoa(peer_sin.sin_addr),
            sizeof client_info[new_socket].ipaddr);
	*/
	/* Port
    client_info[new_socket].port = ntohs(peer_sin.sin_port);
	*/

	/*
    printf("connect: %s (%s) port %d  discripter %d \n",
           client_info[new_socket].hostname,
           client_info[new_socket].ipaddr,
           client_info[new_socket].port,
           new_socket);
	*/
  return new_socket;
}

void read_and_reply(int sock){
  int read_size;
  int ret;
  char buf[BUF_LEN];

  read_size = read(sock, buf, sizeof(buf)-1);

  if ( read_size == -1 ){
	perror("read");
  } else if ( read_size == 0 ){
	printf("number closed [%d]", sock);
	/*
	printf("%s (%s) ポート %d  ディスクリプタ %d 番からの接続が切れました。\n",
		   client_info[sock].hostname,
		   client_info[sock].ipaddr,
		   client_info[sock].port,
		   sock);
	*/
	ret = close(sock);
	if ( ret == -1 ){
	  perror("close");
	  exit(1);
	}
  } else {
	/*
	printf("%s (%s) ポート %d  ディスクリプタ %d 番からのメッセージ: %.*s", 
		   client_info[sock].hostname,
		   client_info[sock].ipaddr,
		   client_info[sock].port,
		   sock,
		   read_size,
		   buf);
	*/
	write(sock, buf, read_size);
  }
}

int main(){
  int sock_optval = 1;
  int ret;
  int port = 5000;
  int listening_socket;
  struct sockaddr_in sin;

  /* listing socket */
  listening_socket = socket(AF_INET, SOCK_STREAM, 0);

  /* socket option */
  if ( setsockopt(listening_socket, SOL_SOCKET, SO_REUSEADDR,
				  &sock_optval, sizeof(sock_optval)) == -1 ){
	perror("setsockopt");
	exit(1);
  }

  /*  */
  sin.sin_family = AF_INET;
  sin.sin_port = htons(port);
  sin.sin_addr.s_addr = htonl(INADDR_ANY);

  ret = bind(listening_socket, (struct sockaddr *)&sin, sizeof(sin));
  if ( ret == -1 ){
	perror("bind");
	exit(1);
  }

  ret = listen(listening_socket, SOMAXCONN);
  if ( ret == -1 ){
	perror("listen");
	exit(1);
  }
  printf("Port %d\n", port);

  int kq;
  struct kevent kev;

  kq = kqueue();
  if ( kq == -1 ){
	perror("kqueue");
	exit(1);
  }
  EV_SET(&kev, listening_socket, EVFILT_READ, EV_ADD, 0, 0, NULL);
  ret = kevent(kq, &kev, 1, NULL, 0, NULL);
  if ( ret == -1 ){
	perror("kevent");
	exit(1);
  }

  while (1){
	int n;
	struct timespec waitspec;
	waitspec.tv_sec  = 2;
	waitspec.tv_nsec = 500000;

	n = kevent(kq, NULL, 0, &kev, 1, &waitspec);

	if ( n == -1 ){
	  perror("kevent");
	  exit(1);
	} else if ( n > 0 ){
	  // readable socket
	  if ( kev.ident == listening_socket ){
		// new client appeared
		int new_sock = accept_new_client(kev.ident);
		if ( new_sock != -1 ){
		  // new socket
		  EV_SET(&kev, new_sock, EVFILT_READ, EV_ADD, 0, 0, NULL);
		  n = kevent(kq, &kev, 1, NULL, 0, NULL);
		  if ( n == -1 ){
			perror("kevent");
			exit(1);
		  }
		}
	  } else {
		int sock = kev.ident;
		//printf("ディスクリプタ %d 番が読み込み可能です。\n", sock);
		read_and_reply(sock);
	  }
	}
  }
    
  close(listening_socket);
  return 0;
}
