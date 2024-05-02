import { Button } from "src/components"

import { userProfilePicture } from "src/assets/images"
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants"

import "./style.scss"

const PersonalInformation = () => {
  return (
    <div className="personal-information-container">
        <div className="general-information">
            <div className="general-information-header">
                <span className="body-semibold">Personal Information</span>
            </div>
            <div className="general-information-columns">
                <div className="general-information-column">
                    <img src={ userProfilePicture } alt="Profile Picture Holder" className="user-profile-picture"/>
                    <Button label={ BUTTON_LABELS.CHANGE_PHOTO } variant={ BUTTON_VARIANTS.OUTLINED }> </Button>
                </div>
                <div className="general-information-column">
                    Something
                </div>
            </div>
        </div>
    </div>
  )
}

export default PersonalInformation
