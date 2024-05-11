import { useFormContext } from "react-hook-form";
import { useState } from "react";

import { SelectField, CustomFileUploader } from "src/components"; 

import "./style.scss";

const InputField = ({ name, label, type, rules, step, options, className, placeholder, onSelectChange }) => {
    const { register, setValue, formState: { errors } } = useFormContext();
    const [dateValue, setDateValue] = useState(""); 
    const [files, setFiles] = useState([]);
        
    const onChangeDate = (event) => {
        setDateValue(event.target.value);
        setValue(name, event.target.value);
    };

    const renderField = () => {
        const inputProps = {
            ...register(name, { ...rules }),
            id: name,
            type: type,
            className: errors[name] ? "error" : "",
            ...(step && { step }) ,
            placeholder: placeholder || ""
        };
    
        switch (type) {
            case "date": 
                return (
                    <input
                        { ...inputProps }
                        type="date"
                        value={ dateValue }
                        onChange={ onChangeDate } 
                        style={ !dateValue ? { color: "transparent" } : {} } 
                    />
                );
            case "textarea": 
                return <textarea { ...inputProps } />;
            case "select":
                return <SelectField 
                    name={ name } 
                    options={ options } 
                    rules={ rules } 
                    label={ label } 
                    onSelectChange={ onSelectChange }
                />;
            case "file":
                    return <CustomFileUploader name={name} setFiles={setFiles} setValue={setValue} files={files} />;
            default:
                return <input {...inputProps} />;
        }
    };

    return (
        <div className={`${className} input-field`}>
            <label htmlFor={ name } className="body-semibold">{ label }</label>
            { renderField() }
            { errors[name] && <span className="error-message body-small-regular">{ errors[name].message }</span> }
        </div>
    );
}

export default InputField;
