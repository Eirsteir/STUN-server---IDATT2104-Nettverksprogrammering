import React from "react";
import { v1 as uuid } from "uuid";
import '../styles/CreateRoom.css';

const CreateRoom = (props) => {
    function create() {
        const id = uuid();
        props.history.push(`/room/${id}`);
    }

    return (
        <div className="btn-page">
            <div className="btn-container">
            <button onClick={create}>Create Room</button>
            </div>
        </div>
    );
}

export default CreateRoom;