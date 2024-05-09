import { useFormContext } from "react-hook-form";
import { useState } from "react";
import { FileUploader } from "react-drag-drop-files";

import { SelectField, ErrorComponent } from "src/components"; 

import "./style.scss";

const InputField = ({ name, label, type, rules, step, options, className, placeholder, onSelectChange }) => {
    const { register, setValue, formState: { errors } } = useFormContext();
    const [dateValue, setDateValue] = useState(""); 
    const [files, setFiles] = useState([]);
    
    const onChangeDate = (event) => {
        setDateValue(event.target.value);
        setValue(name, event.target.value);
    };

    const handleFileChange = (fileList) => {
        const filesArray = Array.from(fileList);

        // local state
        setFiles(prevFiles => [...prevFiles, ...filesArray]);
        // form state
        setValue(name, filesArray, { shouldValidate: true });
    };

    const fileTypes = ["JPG", "PNG", "GIF"];

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
            case "select":
                return <SelectField 
                    name={ name } 
                    options={ options } 
                    rules={ rules } 
                    label={ label } 
                    onSelectChange={ onSelectChange }
                />;
            case "file": 
                return <>
                <FileUploader 
                    handleChange={ handleFileChange } 
                    name="file" 
                    types={ fileTypes } 
                    multiple={ true }
                > 
                    <div className="file-upload-container">
                        <span className="file-upload-header body-bold">Upload photos</span>
                        <span className="file-upload-text body-regular">or just drag and drop</span>
                        <span className="file-upload-info body-regular">(Add at least 3 files)</span>
                    </div>
                </FileUploader>
                { files.length > 0 && (
                    <div className="uploaded-files body-regular">
                        { files.map((file, index) => (
                            <div key={ index } className="file-name">
                                { file.name }
                            </div>
                        )) }
                    </div>
                ) }
                </>
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
