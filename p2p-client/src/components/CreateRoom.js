import React, { useState } from "react";
import { v1 as uuid } from "uuid";
import '../styles/CreateRoom.css';


const CreateRoom = (props) => {

    const [joinID, setID] = useState("");
    const [showJoinForm, setShowJoinForm] = useState(false);
    const [showButtons, setShowButtons] = useState(true);

    function create() {
        const id = uuid();
        props.history.push(`/room/${id}`);
    }
    
    function join() {
        props.history.push(`/room/${joinID}`);
    }

    function handleChange(e) {
        setID(e.target.value);
    }

    const Buttons = () => (
        <div className="buttons">
            <button className="create-btn" id="b1" onClick={create}>Create Room</button>
            <button className="create-btn" onClick={showJoin}>Join Room</button>
        </div>
    )

    const JoinForm = () => (
        <div id="join-form">
            <button type="text" className="join-btn" onClick={hideJoin}>X</button>
            <input type="text" className="join-text" placeholder="Input chat ID here" value={joinID} onChange={handleChange}></input>
            <button className="join-btn" onClick={join}>Join</button>
        </div>
    )

    const showJoin = () => {
        setShowJoinForm(true);
        setShowButtons(false);
    }

    const hideJoin = () => {
        setShowButtons(true);
        setShowJoinForm(false);
    }

    return (
        <div className="btn-page">
            <div className="btn-container">
            { showButtons ? <Buttons/> : null }
            { showJoinForm ? <JoinForm/> : null }
            </div>
        </div>
    );
}

export default CreateRoom;