import React from "react";
import '../styles/Messages.css';


const Messages = (props) => {
    const { messages } = props;
    
    const renderMessage = (message, index) => {
        if (message.yours) {
            return (
                <div className="my_message_container" key={index}>
                    <div className="my_message">
                        {message.value}
                    </div>
                </div>
            )
        }

        return (
            <div className="received_message_container" key={index}>
                <div className="received_message">
                    {message.value}
                </div>
            </div>
        )
    }

    return (
        <div className="messages_container">
            {messages.map(renderMessage)}
        </div>
    )
}

export default Messages;