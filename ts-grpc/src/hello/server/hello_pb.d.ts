// package: hellosrv
// file: pb/server/hello.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from "google-protobuf";
import * as pb_models_hello_pb from "../models/hello_pb";

export class Say extends jspb.Message { 
    getMessage(): string;
    setMessage(value: string): Say;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): Say.AsObject;
    static toObject(includeInstance: boolean, msg: Say): Say.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: Say, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): Say;
    static deserializeBinaryFromReader(message: Say, reader: jspb.BinaryReader): Say;
}

export namespace Say {
    export type AsObject = {
        message: string,
    }
}
