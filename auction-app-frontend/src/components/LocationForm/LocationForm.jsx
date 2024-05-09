import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FormContainer } from "src/components";

import { locationShippingFormFields, cardInformationFields } from "src/forms/fields";
import { BUTTON_LABELS, ROUTE_PATHS } from "src/constants";

import "./style.scss";

const LocationForm = ({ formData, setFormData, handleFinalSubmit }) => {
    const navigate = useNavigate();

    const [showCardInfo, setShowCardInfo] = useState(false);

    const methods = useForm({
        mode: "onBlur", 
        defaultValues: formData
    });

    const onSubmit = () => {
        const data = methods.getValues();

        if (!showCardInfo) {
            setFormData({...formData, data});
            setShowCardInfo(true);
        } else {
            setFormData({...formData, data});
            handleFinalSubmit();
        }
    };

    const onCancel = () => {
        navigate(ROUTE_PATHS.MY_ACCOUNT);
    };

    const onBack = () => {
        if (showCardInfo) {
            setShowCardInfo(false);
        } else {
            navigate("#prices");
        }
    };

    const combinedFormFields = showCardInfo ? 
        [...locationShippingFormFields, ...cardInformationFields] : locationShippingFormFields;

    return (
        <div className="location-form form">
            <div className="form-header">
                <h5>LOCATION & SHIPPING</h5>
            </div>
            <div className="form-fields">
                <FormContainer 
                    formFields={ combinedFormFields }
                    onSubmit={ methods.handleSubmit(onSubmit) }
                    onCancel={ onCancel }
                    onBack={ onBack }
                    buttonLabel={ BUTTON_LABELS.NEXT }
                    cancelLabel={ BUTTON_LABELS.CANCEL }
                    backLabel={ BUTTON_LABELS.BACK }
                    methods= {methods }
                />
            </div>
        </div>
    );
};

export default LocationForm;
