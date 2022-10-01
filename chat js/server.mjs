
import ws from "ws";
import {WebSocketServer} from "ws";
import {v4 as uuid} from "uuid";
import {writeFile, readFileSync, existsSync} from "fs";
const clients = {};
const log = existsSync('log') && readFileSync('log', 'utf-8');
const messages = log ? JSON.parse(log) : [];
const wss = new WebSocketServer({port: 8080});
wss.on("connection", (WebSocketServer) => {
    const id = uuid();
    clients[id] = WebSocketServer;

    console.log(`New client ${id}`);

    WebSocketServer.send(JSON.stringify(messages));

        WebSocketServer.on('message', (rawMessage) => {

            const {name, message} = JSON.parse(rawMessage);
            messages.push({name, message});
            for (const id in clients) {
                clients[id].send(JSON.stringify([{name, message}]))
            }
        })

    WebSocketServer.on('close', () => {
        delete clients[id];
        console.log(`Client is closed ${id}`)
    })
})

process.on('SIGINT', () => {
    wss.close();
    writeFile('log', JSON.stringify(messages), err => {
        if (err) {
            console.log(err);
        }
        process.exit();
    })
})