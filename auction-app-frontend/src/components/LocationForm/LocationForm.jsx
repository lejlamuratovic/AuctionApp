import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FormContainer } from "src/components";

import { locationShippingFormFields } from "src/forms/fields";
import { BUTTON_LABELS, ROUTE_PATHS } from "src/constants";

import "./style.scss"

const LocationForm = ({ formData, setFormData, handleFinalSubmit }) => {
    const navigate = useNavigate();

    const methods = useForm({
        mode: "onBlur", 
        defaultValues: formData
    });

    const onSubmit = (data) => {
        setFormData(data);
        handleFinalSubmit();
    };

    const onCancel = () => {
        navigate(ROUTE_PATHS.MY_ACCOUNT);
    }

    const onBack = () => {
        navigate("#prices");
    }

    return (
        <div className="location-form form">
            <div className="form-header">
                <h5> LOCATION & SHIPPING </h5>
            </div>
            <div className="form-fields">
                <FormContainer 
                    formFields={ locationShippingFormFields } 
                    onSubmit={ methods.handleSubmit(onSubmit) } 
                    onCancel={ onCancel }
                    onBack = { onBack }
                    buttonLabel={ BUTTON_LABELS.NEXT }
                    cancelLabel={ BUTTON_LABELS.CANCEL }
                    backLabel={ BUTTON_LABELS.BACK }
                    methods={ methods }
                />
            </div>
        </div>
    )
}

export default LocationForm;
