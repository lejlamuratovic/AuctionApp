import { rules } from 'src/forms/rules';

export const placeBidsFormFields = [
    {
        type: "text",
        name: "bidAmount",
        rules: {
            ...rules.required("Bid amount")
        }
    }
];
