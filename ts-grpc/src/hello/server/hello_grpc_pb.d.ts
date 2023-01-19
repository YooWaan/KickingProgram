// package: hellosrv
// file: pb/server/hello.proto

/* tslint:disable */
/* eslint-disable */

import * as grpc from "grpc";
import * as pb_server_hello_pb from "./hello_pb";
import * as pb_models_hello_pb from "../models/hello_pb";

interface IHelloServiceService extends grpc.ServiceDefinition<grpc.UntypedServiceImplementation> {
    greet: IHelloServiceService_IGreet;
}

interface IHelloServiceService_IGreet extends grpc.MethodDefinition<pb_models_hello_pb.Hey, pb_server_hello_pb.Say> {
    path: "/hellosrv.HelloService/Greet";
    requestStream: false;
    responseStream: false;
    requestSerialize: grpc.serialize<pb_models_hello_pb.Hey>;
    requestDeserialize: grpc.deserialize<pb_models_hello_pb.Hey>;
    responseSerialize: grpc.serialize<pb_server_hello_pb.Say>;
    responseDeserialize: grpc.deserialize<pb_server_hello_pb.Say>;
}

export const HelloServiceService: IHelloServiceService;

export interface IHelloServiceServer {
    greet: grpc.handleUnaryCall<pb_models_hello_pb.Hey, pb_server_hello_pb.Say>;
}

export interface IHelloServiceClient {
    greet(request: pb_models_hello_pb.Hey, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
    greet(request: pb_models_hello_pb.Hey, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
    greet(request: pb_models_hello_pb.Hey, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
}

export class HelloServiceClient extends grpc.Client implements IHelloServiceClient {
    constructor(address: string, credentials: grpc.ChannelCredentials, options?: object);
    public greet(request: pb_models_hello_pb.Hey, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
    public greet(request: pb_models_hello_pb.Hey, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
    public greet(request: pb_models_hello_pb.Hey, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: pb_server_hello_pb.Say) => void): grpc.ClientUnaryCall;
}
