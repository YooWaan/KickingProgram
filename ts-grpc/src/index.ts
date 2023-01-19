
import { Hey } from "hello/models/hello_pb";
import { HelloServiceClient } from "hello/server/hello_pb";

import { credentials } from "@grpc/grpc-js";


const serverURL = `localhost:8080`
const grpcClientOptions = {
  "grpc.lb_policy_name": "round_robin",
  "grpc.dns_min_time_between_resolutions_ms": 5000,
  "grpc.keepalive_timeout_ms": 1000,
};

const client = new HelloServiceClient(serverURL, credentials.createInsecure(), grpcClientOptions)



function hello(txt: string) : Promise {
    const hey = new Hey()
    hey.setText(txt)

    return new Promise((resolve, reject) => {
        client.greet(hey, (error, response) => {
            if (error) {
                reject(error)
            } else {
                resolve(response)
            }
        })
    })
}


async function callServer() : void {
    try {
        const res = await hello("bbbb");
        console.log(res);
    } catch (error) {
        console.log(error);
    }
}


callServer()
