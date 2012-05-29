#import <Foundation/Foundation.h>
#import <CoreFoundation/CFSocket.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>


void server_received(CFSocketRef s, CFSocketCallBackType callbackType, CFDataRef address, const void *data, void *info);

void server_accept(CFSocketRef s, CFSocketCallBackType callbackType, CFDataRef address, const void *data, void *info);

@interface FsServer : NSObject

@property (nonatomic) NSInteger port;

-(void) start;

-(void) receive:(CFSocketRef)s type:(CFSocketCallBackType)type address:(CFDataRef)address data:(const void *)data info:(void *)info;

-(void) accept:(CFSocketRef)s type:(CFSocketCallBackType)type address:(CFDataRef)address data:(const void *)data info:(void *)info;

@end

@implementation FsServer

@synthesize port;

-(void) start {
  struct sockaddr_in sin;
  int sock, yes = 1;
  CFSocketRef s;
  CFRunLoopSourceRef source;
  CFSocketContext context;
  context.version = 0;
  context.info = (void*)self;
  context.retain = NULL;
  context.release = NULL;
  context.copyDescription = NULL;

  sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
  memset(&sin, 0, sizeof(sin));
  sin.sin_family = AF_INET;
  sin.sin_port = htons(self.port);
  setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes));
  setsockopt(sock, SOL_SOCKET, SO_REUSEPORT, &yes, sizeof(yes));
  bind(sock, (struct sockaddr *)&sin, sizeof(sin));
  listen(sock, 5);

  NSLog(@"start [%ld]", self.port);

  s = CFSocketCreateWithNative(NULL, sock, kCFSocketAcceptCallBack, 
							   server_accept,
							   &context);

  NSLog(@"create socket");

  source = CFSocketCreateRunLoopSource(NULL, s, 0);
  CFRunLoopAddSource(CFRunLoopGetCurrent(), source, kCFRunLoopDefaultMode);
  CFRelease(source);
  CFRelease(s);
  CFRunLoopRun();
}

-(void) receive:(CFSocketRef)s type:(CFSocketCallBackType)type address:(CFDataRef)address data:(const void *)data info:(void *)info {
  CFDataRef df = (CFDataRef) data;
  int len = CFDataGetLength(df);
  if(len <= 0) return;
	
  UInt8 buffer[len];
  CFRange range = CFRangeMake(0,len);
  CFDataGetBytes(df, range, buffer);

  NSString* filePath = [[[NSString alloc] initWithData:(NSData*)df encoding:NSUTF8StringEncoding] autorelease];
  filePath = [filePath stringByTrimmingCharactersInSet:[NSCharacterSet newlineCharacterSet]];
  NSFileManager* manager = [NSFileManager defaultManager];
  if ([manager fileExistsAtPath:filePath]) {
	//NSLog(@"file read [%@]", filePath);
	//CFSocketSendData(s, address, (CFDataRef)[NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil], 0);
	CFSocketSendData(s, address, (CFDataRef)[NSData dataWithContentsOfFile:filePath], 0);
  } else {
	CFSocketSendData(s, address, (CFDataRef)[[NSString stringWithFormat:@"file not found [%@].\n" , filePath] dataUsingEncoding:NSUTF8StringEncoding], 0);	
  }
}

-(void) accept:(CFSocketRef)s type:(CFSocketCallBackType)type address:(CFDataRef)address data:(const void *)data info:(void *)info {
  CFSocketNativeHandle csock = *(CFSocketNativeHandle *)data;
  CFSocketRef sn;
  CFRunLoopSourceRef source;
  CFSocketContext context;
  context.version = 0;
  context.info = (void*)self;
  context.retain = NULL;
  context.release = NULL;
  context.copyDescription = NULL;

	
  NSLog(@"Server socket %d received connection socket %d\n",  CFSocketGetNative(s), csock);
  sn = CFSocketCreateWithNative(NULL, csock, kCFSocketDataCallBack,
								server_received,
								&context);
  source = CFSocketCreateRunLoopSource(NULL, sn, 0);
  CFRunLoopAddSource(CFRunLoopGetCurrent(), source, kCFRunLoopDefaultMode);
  CFRelease(source);
  CFRelease(sn);
}

@end


void server_received(CFSocketRef s, CFSocketCallBackType callbackType, CFDataRef address, const void *data, void *info) {
  FsServer* server = (FsServer*)info;
  [server receive:s type:callbackType address:address data:data info:info];
}

void server_accept(CFSocketRef s, CFSocketCallBackType callbackType, CFDataRef address, const void *data, void *info) {
  FsServer* server = (FsServer*)info;
  [server accept:s type:callbackType address:address data:data info:info];
}

int main () { 
  FsServer* server = [[[FsServer alloc] init] retain];
  server.port = 5000;
  [server start];
  [server release];
}
