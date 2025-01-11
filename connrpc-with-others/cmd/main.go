package main

import (
	"context"
	"fmt"
	"log"
	"net/http"

	"golang.org/x/net/http2"
	"golang.org/x/net/http2/h2c"

	"connectrpc.com/connect"
	"github.com/gin-gonic/gin"

	helo "example.com/connrpcothr/rpc/hello/v1"
	conn "example.com/connrpcothr/rpc/hello/v1/v1connect"
)

type Server struct{}

func (s *Server) Greet(c context.Context, in *connect.Request[helo.Request]) (*connect.Response[helo.Response], error) {
	return connect.NewResponse(&helo.Response{Msg: fmt.Sprintf("Greet :%v", in.Msg.Text)}), nil
}

func main() {
	mux := http.NewServeMux()

	// connectrpc
	s := &Server{}
	path, hdr := conn.NewHeloServiceHandler(s)
	mux.Handle(path, hdr)
	log.Printf("HeloService Path: %v", path)

	// std
	ptn := "GET /std/n/{num}"
	mux.Handle(ptn, http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		txt := r.PathValue("num")
		w.Header().Set("Content-Type", "plain/text")
		w.WriteHeader(http.StatusOK)
		w.Write([]byte(fmt.Sprintf("std: %s", txt)))
	}))
	log.Printf("Std : %s", ptn)

	// gin
	egn := gin.Default()
	api := egn.Group("api/v1")

	api.GET("/h/:text", func(c *gin.Context) {
		txt := c.Param("text")
		c.JSON(http.StatusOK, gin.H{
			"msg": fmt.Sprintf("gin: %s", txt),
		})
	})
	mux.Handle("/", egn)

	if err := http.ListenAndServe("localhost:8080", h2c.NewHandler(mux, &http2.Server{})); err != nil {
		log.Fatalf("down: %v", err)
	}
}
