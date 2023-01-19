package main

import (
	"context"
	"fmt"
	"log"
	"net"

	"google.golang.org/grpc"

	pb "github.com/hello/grpc/pkg/models"
	gsrv "github.com/hello/grpc/pkg/server"
)

type server struct {
	gsrv.UnimplementedHelloServiceServer
}

func (s *server) Greet(c context.Context, h *pb.Hey) (*gsrv.Say, error) {
	return &gsrv.Say{
		Message: fmt.Sprintf("hello %v", h.Text),
	}, nil
}

func main() {

	li, err := net.Listen("tcp", "localhost:8080")
	if err != nil {
		log.Fatalf("Failed boot %v", err)
	}

	srv := grpc.NewServer()

	gsrv.RegisterHelloServiceServer(srv, &server{})

	log.Printf("start server 8080")
	if err := srv.Serve(li); err != nil {
		log.Fatalf("Failed : %v", err)
	}
}
