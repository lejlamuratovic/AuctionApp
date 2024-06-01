import { useEffect, useState } from "react";

import { Button } from "src/components";

import { BUTTON_LABELS } from "src/constants";
import { useUser } from "src/store/UserContext";
import { getUser } from "src/services/userService";

import "./style.scss"

const SettingsTab = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const { userId } = useUser();
  
    const fetchUserInformation = () => {
      setLoading(true);
  
      getUser(userId)
        .then((response) => {
          setUser(response);
          setLoading(false);
          console.log("User information fetched successfully", response);
        })
        .catch((error) => {
          setError(error);
          setLoading(false);
        })
        .finally(() => {
          setLoading(false);
        });
    }
  
    useEffect(() => {
      if (userId) fetchUserInformation();
    }, [userId]);

    return (
        <div className="settings-tab-container">
            <div className="box-container body-semibold">
                <div className="box top-right">
                    <span className="box-heading">Contact Information</span>
                    <span className="box-description">This information can be edited on your profile.</span>
                    <span className="box-heading">Email <span className="box-information body-semibold">{ user?.email }</span></span>
                </div>
                <div className="box bottom-left">
                    <span className="box-heading">Account Information</span>
                    <span className="box-description">Do you want to deactivate account?</span>
                    <Button label={ BUTTON_LABELS.DEACTIVATE } disabled={ true }/>
                </div>
            </div>
        </div>
    )
}

export default SettingsTab;
