import { rules } from 'src/forms/rules';

export const addressInformationFields = [
    {
        label: "Street",
        type: "text",
        name: "street",
        rules: rules.street()  
    },
    {
        label: "City",
        type: "text",
        name: "city",
        rules: rules.city()
    },
    {
        label: "Zip Code",
        type: "number",
        name: "zipCode",
        rules: {
            ...rules.required("Zip Code"),
        }
    }, 
    {
        label: "State",
        type: "text",
        name: "state",
        rules: rules.state()
    },
    {
        label: "Country",
        type: "text",
        name: "country",
        rules: rules.country()
    }
];
