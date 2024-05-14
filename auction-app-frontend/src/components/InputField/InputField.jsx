import { useState } from "react";
import { useFormContext } from "react-hook-form";

import "./style.scss";

const InputField = ({ name, label, type, rules, step, className, placeholder }) => {
    const { register, setValue, formState: { errors } } = useFormContext();

    const [dateValue, setDateValue] = useState("");

    const onChangeDate = (event) => {
        setDateValue(event.target.value);
        setValue(name, event.target.value);
    };

    const inputProps = {
        ...register(name, { ...rules }),
        id: name,
        className: errors[name] ? "error" : "",
        placeholder: placeholder || ""
    };

    return (
        <div className={ `${ className } input-field` }>
            <label htmlFor={ name } className="body-semibold">{ label }</label>
            { type === "date" ? (
                <input { ...inputProps } type="date" onChange={ onChangeDate } />
            ) : (
                <input { ...inputProps } type={ type } step={ type === "number" ? step : undefined } />
            ) }
            { errors[name] && <span className="error-message body-small-regular">{ errors[name].message }</span> }
        </div>
    );
};

export default InputField;
