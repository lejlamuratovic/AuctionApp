import { useForm } from "react-hook-form"

import { Button, FormContainer } from "src/components"

import { userProfilePicture } from "src/assets/images"
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants"
import { personalInformationFormFields } from "src/forms/fields"

import "./style.scss"

const PersonalInformation = () => {
    const methods = useForm({
        mode: "onBlur"
    }); 

    const onSubmit = (data) => {
        console.log(data);
    }

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
                        <FormContainer 
                            formFields={ personalInformationFormFields } 
                            onSubmit={ methods.handleSubmit(onSubmit) } 
                            buttonLabel={ BUTTON_LABELS.SAVE_INFO }
                            buttonVariant={ BUTTON_VARIANTS.OUTLINED }
                            methods={ methods }
                        />
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PersonalInformation
