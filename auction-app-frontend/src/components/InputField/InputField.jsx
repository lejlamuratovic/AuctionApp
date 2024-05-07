import { useState } from "react";
import { useFormContext } from "react-hook-form";
import { FileUploader } from "react-drag-drop-files";

import { ErrorComponent } from "src/components";

import "./style.scss";

const InputField = ({ name, label, type, rules, step, options, className }) => {
    const [files, setFiles] = useState([]);

    const handleFileChange = (uploadedFiles) => {
        if (Array.isArray(uploadedFiles)) {
            setFiles((prevFiles) => [...prevFiles, ...uploadedFiles]);
        } else if (uploadedFiles instanceof FileList) {
            const fileListArray = Array.from(uploadedFiles);

            setFiles((prevFiles) => [...prevFiles, ...fileListArray]);
        } else {
            <ErrorComponent message="Invalid file type" />
        }
    };
    
    
    const { register, formState: { errors } } = useFormContext();

    const fileTypes = ["JPG", "PNG", "GIF"];

    const renderField = () => {
        const inputProps = {
            ...register(name, { ...rules }),
            id: name,
            type: type,
            className: errors[name] ? "error" : "",
            ...(step && { step }) 
        };
    
        switch (type) {
            case "select":
                return "SelectField";
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
