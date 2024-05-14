import { useEffect } from "react";
import { FileUploader } from "react-drag-drop-files";
import { useFormContext } from "react-hook-form";

import { FILE_TYPES } from "src/constants";

import "./style.scss";

const CustomFileUploader = ({ name, setFiles, setValue, files }) => {
    const { watch } = useFormContext();
    
    const currentValue = watch(name);

    useEffect(() => {
        if (currentValue) setFiles(currentValue);
    }, [currentValue]);

    const handleFileChange = (fileList) => {
        const filesArray = Array.from(fileList);

        setFiles(prevFiles => [...prevFiles, ...filesArray]);
        setValue(name, [...files, ...filesArray]);
    };


    return (
        <>
            <FileUploader
                handleChange={ handleFileChange }
                name={ name }
                types={ FILE_TYPES }
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
                        <div key={ `${ file.name + "-" + index }` } className="file-name">
                            { file.name }
                        </div>
                    )) }
                </div>
            ) }
        </>
    );
};

export default CustomFileUploader;
