import { rules } from 'src/forms/rules';

export const addressInformationFields = [
    {
        label: "Street",
        type: "text",
        name: "street",
        rules: {
            ...rules.required("Street")
        }
    },
    {
        label: "City",
        type: "text",
        name: "city",
        rules: {
            ...rules.required("")
        }
    },
    {
        label: "Zip Code",
        type: "number",
        name: "zipCode",
        rules: {
            ...rules.required("")
        }
    }, 
    {
        label: "State",
        type: "text",
        name: "state",
        rules: {
            ...rules.required("")
        }
    },
    {
        label: "Country",
        type: "text",
        name: "country",
        rules: {
            ...rules.required("")
        }
    }
];