import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import Modal from "react-modal";
import { FileUploader } from "react-drag-drop-files";

import { Button, FormContainer, InputField, CustomFileUploader } from "src/components";

import { userProfilePicture } from "src/assets/images";
import { dropdownInactive, dropdownActive } from "src/assets/icons";
import { BUTTON_LABELS, BUTTON_VARIANTS } from "src/constants";
import { personalInformationFormFields, profileCardInformationFields, profileAddressInformationFields } from "src/forms/fields";
import { useUser } from "src/store/UserContext";
import { getUser, updateUser } from "src/services/userService"
import { close } from "src/assets/icons";
import { FILE_TYPES } from "src/constants";

import "./style.scss";

const ProfileTab = () => {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState();
    const [error, setError] = useState();

    const methods = useForm({
        mode: "onBlur",
        defaultValues: {
            firstName: '',
            lastName: '',
            email: '',
            address: '',
            city: '',
            country: '',
            zipCode: '',
            nameOnCard: '',
            cardNumber: '',
            expirationMonth: '',
            expirationYear: '',
            cvv: '',
            month: '',
            day: '',
            year: '',
            street: ''
        }
    });

    const { reset } = methods;
    const { userId } = useUser();

    const [showCardInfo, setShowCardInfo] = useState(false);
    const [showAddressInfo, setShowAddressInfo] = useState(false);
    const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
    const [profilePicture, setProfilePicture] = useState(userProfilePicture);

    const toggleCreditCardInfo = () => setShowCardInfo(!showCardInfo);
    const toggleAddressInfo = () => setShowAddressInfo(!showAddressInfo);

    const fetchUserInformation = () => {
        setLoading(true);
    
        getUser(userId)
          .then((response) => {
            setUser(response);

            if(response.profilePicture) {
                setProfilePicture(response.profilePicture);
            }

            setLoading(false);

            let expMonth = '';
            let expYear = '';

            if (response.paymentInfoEntity?.creditCardEntity.expirationDate) {
                const expDate = new Date(response.paymentInfoEntity?.creditCardEntity.expirationDate);
                expMonth = expDate?.getMonth() + 1;
                expYear = expDate?.getFullYear();
            }

            let month = '';
            let day = '';
            let year = '';

            if (response.dob) {
                const dob = new Date(response.dob);
                month = dob?.getMonth() + 1;
                day = dob?.getDate();
                year = dob?.getFullYear();
            }

            reset({
                firstName: response.firstName,
                lastName: response.lastName,
                email: response.email,
                street: response.paymentInfoEntity?.address,
                city: response.paymentInfoEntity?.city,
                country: response.paymentInfoEntity?.country,
                zipCode: response.paymentInfoEntity?.zipCode,
                nameOnCard: response.paymentInfoEntity?.creditCardEntity?.nameOnCard,
                cardNumber: response.paymentInfoEntity?.creditCardEntity?.cardNumber,
                expirationMonth: expMonth,
                expirationYear: expYear,
                month: month,
                day: day,
                year: year
            });
          })
          .catch((error) => {
            setError(error);
            setLoading(false);
          })
          .finally(() => {
            setLoading(false);
          });
    };

    const updateUserData = (data) => {
        // null fields because some fields are optional
        const userData = {
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            dob: data.year && data.month && data.day ? new Date(data.year, data.month - 1, data.day) : null,
            address: data.street || null,
            city: data.city || null,
            country: data.country || null,
            zipCode: data.zipCode || null,
            nameOnCard: data.nameOnCard || null,
            cardNumber: data.cardNumber || null,
            expirationDate: data.expirationYear && data.expirationMonth ? new Date(data.expirationYear, data.expirationMonth - 1, 1) : null
        };    

        updateUser(userId, userData)
            .then((response) => {
                console.log(response);
            }).catch((error) => {
                console.log(error);
            });
    };
    
    useEffect(() => {
        if (userId) fetchUserInformation();
    }, [userId, reset]);

    const onSubmit = (data) => {
        updateUserData(data);
    }

    const changeProfilePicture = () => {
        setIsProfileModalOpen(true);
    };
    
    const closeProfileModal = () => {
        setIsProfileModalOpen(false);
    };  

    const handleFileChange = (file) => {
        setProfilePicture(URL.createObjectURL(file));
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
                            <img src={ profilePicture } alt="Profile Picture" className="user-profile-picture"/>
                            <Button 
                                label={ BUTTON_LABELS.CHANGE_PHOTO } 
                                variant={ BUTTON_VARIANTS.OUTLINED } 
                                onButtonClick={ changeProfilePicture }
                                type="button" />
                            { isProfileModalOpen && (
                                <Modal 
                                    isOpen={ isProfileModalOpen } 
                                    onRequestClose={ closeProfileModal } 
                                    contentLabel="Profile"
                                    className="profile-modal"
                                    overlayClassName="modal-overlay"
                                    appElement={ document.getElementById('root') }
                                >
                                    <img src = { close } alt="Close" className="close-icon" onClick={ closeProfileModal } />
                                    <img src={ profilePicture } alt="Profile Picture" className="user-profile-picture" />
                                    <FileUploader
                                        handleChange={ handleFileChange }
                                        name="profilePicture"
                                        types={ FILE_TYPES }
                                        multiple={ false }
                                        classes="file-uploader"
                                    />
                                    <Button 
                                        label={ BUTTON_LABELS.UPLOAD } 
                                        variant={ BUTTON_VARIANTS.OUTLINED } 
                                        onButtonClick={ closeProfileModal }
                                    />
                                </Modal>
                            ) }
                        </div>
                        <div className="general-information-column">
                            <div className="profile-form">
                                { personalInformationFormFields.map(field => (
                                    <InputField
                                        key={ field.name }
                                        name={ field.name }
                                        label={ field.label }
                                        type={ field.type }
                                        rules={ field.rules }
                                        className={ field.specialClass ? field.specialClass : "" }
                                    />
                                )) }
                            </div>
                        </div>
                    </div>
                </div>
                {/* collapsible sections */}
                <div className="general-information">
                    <div className="general-information-header" onClick={ toggleCreditCardInfo }>
                        <img src={ showCardInfo ? dropdownInactive : dropdownActive } alt="Toggle Additional Information" />
                        <span className="body-semibold">Card Information (Optional)</span>
                    </div>
                    { showCardInfo && (
                        <div className="collapsable-column">
                            <div className="profile-form">
                            { profileCardInformationFields.map(field => (
                                <InputField
                                    key={ field.name } 
                                    name={ field.name }
                                    label={ field.label }
                                    type={ field.type }
                                    rules={ field.rules }
                                    className={ field.specialClass ? field.specialClass : "" }
                                />
                            )) }
                            </div>
                        </div>
                    ) }
                </div>
                <div className="general-information">
                    <div className="general-information-header" onClick={ toggleAddressInfo }>
                        <img src={ showAddressInfo ? dropdownInactive : dropdownActive } alt="Toggle Additional Information" />
                        <span className="body-semibold">Shipping Address (Optional)</span>
                    </div>
                    { showAddressInfo && (
                        <div className="collapsable-column">
                            <div className="profile-form">
                                { profileAddressInformationFields.map(field => (
                                    <InputField
                                        key={ field.name }
                                        name={ field.name }
                                        label={ field.label }
                                        type={ field.type } 
                                        rules={ field.rules }
                                        className={ field.specialClass ? field.specialClass : "" }
                                    />
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
