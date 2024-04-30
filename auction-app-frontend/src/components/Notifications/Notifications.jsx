import { useEffect, useState } from "react";

import { LoadingComponent, ErrorComponent } from "src/components";

import { useUser } from "src/store/UserContext";
import { useWebSocket } from "src/store/WebSocketContext";
import { getLatestNotification } from "src/services/notificationService";

import "./style.scss";

const Notifications = ({ productId, onMessage }) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [message, setMessage] = useState("");

  const { userId } = useUser();
  const { addMessageListener, removeMessageListener } = useWebSocket();

  const fetchLatestNotification = async () => {
    setLoading(true);

    getLatestNotification(userId, productId)
      .then(notification => {
          setMessage(notification.messageContent);
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          setMessage(null); // if no notification found set message to null
        } else { 
          setError(error.message);
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    if (!userId || !productId) {
      setLoading(false);

      return;
    }

    fetchLatestNotification();
  }, [userId, productId]);

  useEffect(() => {
    const handleNewMessage = (rawMessage) => {
      if (typeof rawMessage === 'string') {
        setMessage(rawMessage); // websocket returns a string message
        onMessage(); // refetch product details when a new message is received
      } else {
        if (rawMessage && rawMessage.productId === productId && rawMessage.messageContent) {
          setMessage(rawMessage.messageContent); // if message is for the current product, set message to the message content
        }
      }
    };
  
    addMessageListener(handleNewMessage);
  
    return () => {
      removeMessageListener(handleNewMessage);
    };
  }, [addMessageListener, removeMessageListener, productId]);

  const notificationClass = message && message.includes("Congratulations!") ? "primary" : "accent";

  if (loading) return <LoadingComponent />;
  if (error) return <ErrorComponent message={error} />;  

  return (
    <>
      { message && (
        <div className={ `notification ${notificationClass} body-bold` }>
          { message }
        </div>
      ) }
    </>
  );
};

export default Notifications;
