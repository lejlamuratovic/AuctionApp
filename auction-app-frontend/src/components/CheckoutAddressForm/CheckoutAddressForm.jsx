import { useForm } from "react-hook-form";

import { FormContainer } from "src/components";
import { addressInformationFields } from "src/forms/fields";
import { BUTTON_LABELS } from "src/constants";

import "./style.scss";

const CheckoutAddressForm = ({ onAddressFormSubmit }) => {
    const methods = useForm({
        mode: "onBlur",
        defaultValues: {
            // dummy data
            street: "Some street", 
            city: "Sarajevo",
            zipCode: "123",
            country: "BiH",
            state: "BiH"
        }
    });
    const onSubmit = () => {
        const formData = methods.getValues();

        onAddressFormSubmit(formData);
    }

    return (
        <div className="address-form-container">
            <FormContainer 
                formFields={ addressInformationFields }
                onSubmit={ methods.handleSubmit(onSubmit) }
                buttonLabel={ BUTTON_LABELS.NEXT }
                methods={ methods }
            />
        </div>
    )
}

export default CheckoutAddressForm
