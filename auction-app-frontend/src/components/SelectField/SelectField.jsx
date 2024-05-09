import { useState, useEffect } from "react";
import { useFormContext } from "react-hook-form";

import { dropdown } from "src/assets/icons";

import "./style.scss";

const SelectField = ({ name, options = [], rules, label, onSelectChange }) => {
    const { register, setValue } = useFormContext();
    
    const [selected, setSelected] = useState(null);
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown =  () => setIsOpen(!isOpen);

    const handleSelect = (value) => {
        setSelected(value);
        setValue(name, value);
        setIsOpen(false);
    
        if (onSelectChange) {
            onSelectChange(value);
        }
    };

    useEffect(() => {
        register(name, { ...rules });
    }, [register, name, rules]);
    

    const displayLabel = selected ? options.find(option => option.value === selected)?.label : label;

    return (
        <div className="custom-select body-regular">
            <div className="dropdown-btn" onClick={ toggleDropdown }>
                { displayLabel }
                <img src={ dropdown } alt="Dropdown" />
            </div>
            { isOpen && (
                <div className="dropdown-content">
                    { options.map((option) => (
                        <div key={ option.value } className="dropdown-item" onClick={ () => handleSelect(option.value) }>
                            { option.label }
                        </div>
                    )) }
                </div>
            ) }
        </div>
    );
};

export default SelectField;
