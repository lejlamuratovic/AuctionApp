import { FileUploader } from "react-drag-drop-files";

import "./style.scss";

const CustomFileUploader = ({ name, setFiles, setValue, files }) => {
    const handleFileChange = (fileList) => {
        const filesArray = Array.from(fileList);

        setFiles(prevFiles => [...prevFiles, ...filesArray]);
        setValue(name, [...files, ...filesArray]);
    };
    
    const fileTypes = ["JPG", "PNG", "GIF"];

    return (
        <>
            <FileUploader
                handleChange={ handleFileChange }
                name={ name }
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
                        <div key={` ${ file.name + "-" + index } ` } className="file-name">
                            { file.name }
                        </div>
                    )) }
                </div>
            ) }
        </>
    );
};

export default CustomFileUploader;
