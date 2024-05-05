import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FormContainer } from "src/components";

import { productDetailsFormFields } from "src/forms/fields";
import { BUTTON_LABELS, ROUTE_PATHS } from "src/constants";

import "./style.scss"

const ProductDetailsForm = () => {
    const navigate = useNavigate();

    const methods = useForm({
        mode: "onBlur"
    });

    const onSubmit = (data) => {
        navigate("#prices");
    }

    const onCancel = () => {
        navigate(ROUTE_PATHS.MY_ACCOUNT);
    }

    return (
        <div className="details-form form">
            <div className="form-header">
                <h5> ADD ITEM </h5>
            </div>
            <div className="form-fields">
                <FormContainer 
                    formFields={ productDetailsFormFields } 
                    onSubmit={ methods.handleSubmit(onSubmit) } 
                    onCancel={ onCancel }
                    buttonLabel={ BUTTON_LABELS.NEXT }
                    cancelLabel={ BUTTON_LABELS.CANCEL }
                    methods={ methods }
                />
            </div>
        </div>
    )
}

export default ProductDetailsForm;
