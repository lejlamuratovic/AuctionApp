import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FormContainer } from "src/components";

import { productPriceFormFields } from "src/forms/fields";
import { BUTTON_LABELS, ROUTE_PATHS } from "src/constants";

import "./style.scss"

const ProductPriceForm = () => {
    const navigate = useNavigate();

    const methods = useForm({
        mode: "onBlur"
    });

    const onSubmit = (data) => {
        navigate("#shipping");
    }

    const onCancel = () => {
        navigate(ROUTE_PATHS.MY_ACCOUNT);
    }

    const onBack = () => {
        navigate("#details");
    }

    return (
        <div className="price-form form">
            <div className="form-header">
                <h5> SET PRICES </h5>
            </div>
            <div className="form-fields">
                <FormContainer 
                    formFields={ productPriceFormFields } 
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

export default ProductPriceForm;
