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

#define BUF_LEN  8192
#define MAX_SOCK 256


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
  return new_socket;
}

void read_and_reply(int sock){
  int read_size;
  int ret;
  char buf[BUF_LEN];
  int ch, i;

  read_size = read(sock, buf, sizeof(buf)-1);
  if (buf[read_size - 1] == '\n') {
	for (i = read_size-1; i >= 0; i--) {
	  if (buf[i] == '\n' || buf[i] == '\r') {
		buf[i] = '\0';
	  }
	}
  }

  printf("buf---> %s [%d]\n", buf, read_size);

  if ( read_size == -1 ){
	perror("read");
  } else if ( read_size == 0 ){
	printf("number closed [%d]", sock);
	ret = close(sock);
	if ( ret == -1 ){
	  perror("close error");
	  exit(1);
	}
  } else {
	//printf("--------------- file read\n");
	//printf("access %d \n", access(buf, 0));

	if (access(buf, 0) == 0) {
	  FILE *fp = fopen(buf, "r");
	  if (fp == NULL) {
		sprintf(buf, "file not found [%s]\n", buf);
		write(sock, buf, read_size);
	  } else {
		read_size = 0;
		while ((ch = fgetc(fp)) != EOF) {
		  buf[read_size++] = ch;
		}
		fclose(fp);
		write(sock, buf, read_size);
	  }
	} else {
	  sprintf(buf, "file not found [%s]\n", buf);
	  write(sock, buf, read_size);
	}
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
		read_and_reply(sock);
	  }
	}
  }
    
  close(listening_socket);
  return 0;
}
