import { useState } from "react";
import { useForm } from "react-hook-form";

import { Button, FormContainer, InputField } from "src/components";

import { userProfilePicture } from "src/assets/images";
import { dropdownInactive, dropdownActive } from "src/assets/icons";
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";
import { personalInformationFormFields, cardInformationFields, addressInformationFields } from "src/forms/fields";

import "./style.scss";

const ProfileTab = () => {
    const methods = useForm({
        mode: "onBlur"
    });

    const [showCardInfo, setShowCardInfo] = useState(false);
    const [showAddressInfo, setShowAddressInfo] = useState(false);

    const toggleAdditionalInfo1 = () => setShowCardInfo(!showCardInfo);
    const toggleAdditionalInfo2 = () => setShowAddressInfo(!showAddressInfo);

    const onSubmit = (data) => {
        console.log(data);
    }

    return (
        <div className="profile-tab-container">
            <FormContainer 
                onSubmit={ onSubmit } 
                methods={ methods } 
                buttonLabel={ BUTTON_LABELS.SAVE_INFO }
                buttonVariant={ BUTTON_VARIANTS.OUTLINED }
            >
                {/* general information */}
                <div className="general-information">
                    <div className="general-information-header">
                        <span className="body-semibold">Personal Information</span>
                    </div>
                    <div className="general-information-columns">
                        <div className="general-information-column">
                            <img src={ userProfilePicture } alt="Profile Picture" className="user-profile-picture"/>
                            <Button label={ BUTTON_LABELS.CHANGE_PHOTO } variant={ BUTTON_VARIANTS.OUTLINED } />
                        </div>
                        <div className="general-information-column">
                            { personalInformationFormFields.map(field => (
                                <InputField
                                    key={ field.name }
                                    name={ field.name }
                                    label={ field.label }
                                    type={ field.type }
                                    rules={ field.rules }
                                />
                            )) }
                        </div>
                    </div>
                </div>
                {/* collapsible sections */}
                <div className="general-information">
                    <div className="general-information-header" onClick={ toggleAdditionalInfo1 }>
                        <img src={ showCardInfo ? dropdownInactive : dropdownActive } alt="Toggle Additional Information" />
                        <span className="body-semibold">Card Information (Optional)</span>
                    </div>
                    { showCardInfo && (
                        <div className="collapsable-column">
                            <div className="collapsable-form">
                            { cardInformationFields.map(field => (
                                    <div 
                                        key={ field.name } 
                                        className={
                                            `${field.name === "expirationMonth" 
                                            || field.name === "expirationYear" 
                                            || field.name === "cvv"
                                            ? "row-field card-row" : "column-field"}` }>
                                    <InputField
                                        name={ field.name }
                                        label={ field.label }
                                        type={ field.type }
                                        rules={ field.rules }
                                    />
                                </div>
                                )) }
                            </div>
                        </div>
                    ) }
                </div>
                <div className="general-information">
                    <div className="general-information-header" onClick={ toggleAdditionalInfo2 }>
                        <img src={ showAddressInfo ? dropdownInactive : dropdownActive } alt="Toggle Additional Information" />
                        <span className="body-semibold">Shipping Address (Optional)</span>
                    </div>
                    { showAddressInfo && (
                        <div className="collapsable-column">
                            <div className="collapsable-form">
                                { addressInformationFields.map(field => (
                                    <div 
                                        key={ field.name } 
                                        className={ `${field.name === "zipCode" || field.name === "city" 
                                            ? "row-field" : "column-field"}` }>
                                        <InputField
                                            name={ field.name }
                                            label={ field.label }
                                            type={ field.type } 
                                            rules={ field.rules }
                                            options={ field.type === "select" ? field.options : null }
                                        />
                                    </div>
                                )) }
                            </div>
                        </div>
                    ) }
                </div>
            </FormContainer>
        </div>
    );
}

export default ProfileTab;