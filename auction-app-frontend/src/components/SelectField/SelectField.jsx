import { useState } from "react";
import { useFormContext } from "react-hook-form";

import "./style.scss";

const SelectField = ({ name, options, rules }) => {
    const { register, formState: { errors } } = useFormContext();
    
    const [selected, setSelected] = useState(options[0].value);

    const handleChange = (event) => {
        setSelected(event.target.value);
        register(name, { value: event.target.value, ...rules });
    };

    return (
        <div className="custom-select">
            <select name={ name } value={ selected } onChange={ handleChange } className={ errors[name] ? "error" : "" }>
                { options.map(option => (
                    <option key={ option.value } value={ option.value }>
                        { option.label }
                    </option>
                )) }
            </select>
            { errors[name] && <span className="error-message body-small-regular">{ errors[name].message }</span> }
        </div>
    );
};

export default SelectField;
