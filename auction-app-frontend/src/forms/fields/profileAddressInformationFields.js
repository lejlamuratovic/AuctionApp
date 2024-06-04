import { rules } from 'src/forms/rules';

export const profileAddressInformationFields = [
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
        specialClass: "input-field-half",
        rules: rules.city()
    },
    {
        label: "Zip Code",
        type: "number",
        name: "zipCode",
        specialClass: "input-field-half",
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
