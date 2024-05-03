import React from "react";
import { useEffect } from "react";
import { useFormContext } from "react-hook-form";

import { SelectField } from "src/components"; 

import "./style.scss";

const DobField = ({ name, rules }) => {
    const { setValue, watch, formState: { errors } } = useFormContext();

    const day = watch(`${name}.day`);
    const month = watch(`${name}.month`);
    const year = watch(`${name}.year`);

    const dayOptions = Array.from({ length: 31 }, (_, i) => ({ value: i + 1, label: i + 1 }));
    const monthOptions = Array.from({ length: 12 }, (_, i) => ({ value: i + 1, label: i + 1 }));
    const yearOptions = Array.from({ length: 100 }, (_, i) => {
        const year = new Date().getFullYear() - i;
        return { value: year, label: year };
    });

    useEffect(() => {
        if (day && month && year) {
            setValue(name, `${year}-${month}-${day}`);
        }
    }, [day, month, year, setValue, name]);

    return (
        <div className="dob-container">
            <SelectField name={ `${name}.day` } options={ dayOptions } rules={ rules } />
            <SelectField name={ `${name}.month` } options={ monthOptions } rules={ rules } />
            <SelectField name={ `${name}.year` } options={ yearOptions } rules={ rules } />
            { errors[name] && <span> { errors[name].message } </span> }
        </div>
    );
};

export default DobField;
