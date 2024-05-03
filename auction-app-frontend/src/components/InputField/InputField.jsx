<<<<<<< HEAD
=======
import React from "react";
>>>>>>> 6916e04 (personal information form updated)
import { useFormContext } from "react-hook-form";

import { SelectField, DobField } from "src/components"; 

<<<<<<< HEAD
const InputField = ({ name, label, type, rules, step }) => {
    const { register, formState: { errors } } = useFormContext(); 
=======
import "./style.scss";

const InputField = ({ name, label, type, rules, options }) => {
    const { register, formState: { errors } } = useFormContext();

    const renderField = () => {
        switch (type) {
            case "dob":
                return <DobField name={ name } rules={ rules } />;
            case "select":
                return <SelectField name={ name } options={ options } rules={ rules } />;
            default:
                return <input id={ name } type={ type } { ...register(name, rules) } className={ errors[name] ? "error" : "" } />;
        }
    };
>>>>>>> 6916e04 (personal information form updated)

    const inputProps = {
        ...register(name, { ...rules }),
        id: name,
        type: type,
        className: errors[name] ? "error" : "",
        ...(step && { step })
    };

    return (
        <div className="input-field">
            <label htmlFor={ name } className="body-semibold">{ label }</label>
<<<<<<< HEAD
            <input { ...inputProps } />
=======
            { renderField() }
>>>>>>> 6916e04 (personal information form updated)
            { errors[name] && <span className="error-message body-small-regular">{ errors[name].message }</span> }
        </div>
    );
}

export default InputField;
