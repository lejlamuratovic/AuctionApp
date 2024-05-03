import React from "react";
import { useFormContext } from "react-hook-form";

import { SelectField, DobField } from "src/components"; 

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

    return (
        <div className="input-field">
            <label htmlFor={ name } className="body-semibold">{ label }</label>
            { renderField() }
            { errors[name] && <span className="error-message body-small-regular">{ errors[name].message }</span> }
        </div>
    );
}

export default InputField;
