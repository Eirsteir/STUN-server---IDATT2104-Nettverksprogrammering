import React, { useState } from "react";
import { v1 as uuid } from "uuid";
import '../styles/CreateRoom.css';


const CreateRoom = (props) => {

    const [joinID, setID] = useState("");



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


    

    return (
        <div className="btn-page">
            <div className="btn-container">
            <button className="create-btn" onClick={create}>Create Room</button>
            <input type="text" placeholder="Input chat ID here" value={joinID} onChange={handleChange}></input>
            <button className="create-btn" onClick={join}>Join</button>
            </div>
        </div>
    );
}

export default CreateRoom;