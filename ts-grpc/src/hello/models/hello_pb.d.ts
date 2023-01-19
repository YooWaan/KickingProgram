// package: hello
// file: pb/models/hello.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from "google-protobuf";

export class Hey extends jspb.Message { 
    getText(): string;
    setText(value: string): Hey;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): Hey.AsObject;
    static toObject(includeInstance: boolean, msg: Hey): Hey.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: Hey, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): Hey;
    static deserializeBinaryFromReader(message: Hey, reader: jspb.BinaryReader): Hey;
}

export namespace Hey {
    export type AsObject = {
        text: string,
    }
}
