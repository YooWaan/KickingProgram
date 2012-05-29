#import <Foundation/Foundation.h>
#import <CoreFoundation/CFSocket.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>

void receiveData(CFSocketRef s, CFSocketCallBackType type, 
							  CFDataRef address, const void *data, void *info)
{
	CFDataRef df = (CFDataRef) data;
	int len = CFDataGetLength(df);
	if(len <= 0) return;
	
	UInt8 buffer[len];
	CFRange range = CFRangeMake(0,len);
	NSLog(@"Server received %d bytes from socket %d\n", len, CFSocketGetNative(s));
	CFDataGetBytes(df, range, buffer);
	NSLog(@"Server received: %s\n", buffer); 
	NSLog(@"As UInt8 coding: %@", df);
	
	CFSocketSendData(s, address, df, 0);	// Echo back to client
}

void acceptConnection(CFSocketRef s, CFSocketCallBackType type, CFDataRef address, const void *data, void *info)
{
	CFSocketNativeHandle csock = *(CFSocketNativeHandle *)data;
	CFSocketRef sn;
	CFRunLoopSourceRef source;
	
	NSLog(@"Server socket %d received connection socket %d\n",  CFSocketGetNative(s), csock);
	sn = CFSocketCreateWithNative(NULL, csock, kCFSocketDataCallBack, receiveData, NULL);
	source = CFSocketCreateRunLoopSource(NULL, sn, 0);
	CFRunLoopAddSource(CFRunLoopGetCurrent(), source, kCFRunLoopDefaultMode);
	CFRelease(source);
	CFRelease(sn);
}

int main ()
{ 
	struct sockaddr_in sin;
	int sock, yes = 1;
	CFSocketRef s;
	CFRunLoopSourceRef source;
	
	sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
	memset(&sin, 0, sizeof(sin));
	sin.sin_family = AF_INET;
	sin.sin_port = htons(1234);
	setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes));
	setsockopt(sock, SOL_SOCKET, SO_REUSEPORT, &yes, sizeof(yes));
	bind(sock, (struct sockaddr *)&sin, sizeof(sin));
	listen(sock, 5);
	
	s = CFSocketCreateWithNative(NULL, sock, kCFSocketAcceptCallBack, 
								 acceptConnection, NULL);
	
	source = CFSocketCreateRunLoopSource(NULL, s, 0);
	CFRunLoopAddSource(CFRunLoopGetCurrent(), source, kCFRunLoopDefaultMode);
	CFRelease(source);
	CFRelease(s);
	CFRunLoopRun();
}
