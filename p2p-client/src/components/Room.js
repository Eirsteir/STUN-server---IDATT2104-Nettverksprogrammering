import React, { useRef, useEffect, useState } from "react";
import io from "socket.io-client";
import Messages from '../components/Messages';
import '../styles/Room.css'

const Room = (props) => {
    const peerRef = useRef();
    const socketRef = useRef();
    const otherUser = useRef();
    const sendChannel = useRef();
    const [text, setText] = useState("");
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        socketRef.current = io.connect("https://signaling-server-nettprogg.herokuapp.com/");
        socketRef.current.emit("join room", props.match.params.roomID);

        socketRef.current.on('other user', userID => {
            callUser(userID);
            otherUser.current = userID;
        });

        socketRef.current.on("user joined", userID => {
            otherUser.current = userID;
        });

        socketRef.current.on("offer", handleOffer);

        socketRef.current.on("answer", handleAnswer);

        socketRef.current.on("ice-candidate", handleNewICECandidateMsg);

    }, []);


    function callUser(userID) {
        peerRef.current = createPeer(userID);
        sendChannel.current = peerRef.current.createDataChannel("sendChannel");
        sendChannel.current.onmessage = handleReceiveMessage;
    }

    function handleReceiveMessage(e) {
        setMessages(messages => [...messages, {yours: false, value: e.data}]);
    }

    function createPeer(userID) {
        const peer = new RTCPeerConnection({
            iceServers: [
                {
                    urls: 'stun:nettverksprogg.eastus2.cloudapp.azure.com:3478'
                }
            ]
        });

        peer.onicecandidate = handleICECandidateEvent;
        peer.onnegotiationneeded = () => handleNegotiationNeededEvent(userID);

        return peer;
    }

    function handleNegotiationNeededEvent(userID) {
        peerRef.current.createOffer().then(offer => {
            return peerRef.current.setLocalDescription(offer);
        }).then(() => {
            const payload = {
                target: userID,
                caller: socketRef.current.id,
                sdp: peerRef.current.localDescription
            };
            socketRef.current.emit("offer", payload);
        }).catch(e => console.log(e));
    }

    function handleOffer(incoming) {
        peerRef.current = createPeer();
        peerRef.current.ondatachannel = event => {
            sendChannel.current = event.channel;
            sendChannel.current.onmessage = handleReceiveMessage;
        }
        const desc = new RTCSessionDescription(incoming.sdp);
        
        peerRef.current.setRemoteDescription(desc).then(() => {
        }).then(() => {
            return peerRef.current.createAnswer();
        }).then(answer => {
            return peerRef.current.setLocalDescription(answer);
        }).then(() => {
            const payload = {
                target: incoming.caller,
                caller: socketRef.current.id,
                sdp: peerRef.current.localDescription
            }
            socketRef.current.emit("answer", payload);
        })
    }

    function handleAnswer(message) {
        const desc = new RTCSessionDescription(message.sdp);
        peerRef.current.setRemoteDescription(desc).catch(e => console.log(e));
    }

    function handleICECandidateEvent(e) {
        if (e.candidate) {
            const payload = {
                target: otherUser.current,
                candidate: e.candidate,
            }
            socketRef.current.emit("ice-candidate", payload);
        }
    }

    function handleNewICECandidateMsg(incoming) {
        const candidate = new RTCIceCandidate(incoming);

        peerRef.current.addIceCandidate(candidate)
            .catch(e => console.log(e));
    }

    function sendMessage() {
        sendChannel.current.send(text);
        setMessages(messages => [...messages, {yours: true, value: text}]);
        setText("");
    }

    function handleChange(e) {
        setText(e.target.value);
    }

    const handleKeyDown = (event) => { // TODO: Add ability for Shift+Enter to go to next line
        if (event.key === 'Enter') {
            sendMessage();
        }
    }


    return (
        <div className="container">
            <h1>Chat ID: {props.match.params.roomID}</h1>
            <div className="username">
                <input id="name" type="text" placeholder="Username"></input>
                <button id="name-btn">Submit</button>
            </div>
            <Messages messages={messages} />
            <div className="send-msg">
                <textarea className="msg-box" value={text} onChange={handleChange} onKeyDown={handleKeyDown} placeholder="Write your message here..." />
                <button className="btn" onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
};

export default Room;