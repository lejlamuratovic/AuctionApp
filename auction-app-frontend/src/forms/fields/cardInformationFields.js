import { rules } from 'src/forms/rules';

export const cardInformationFields = [
    {
        label: "Name on Card",
        type: "text",
        name: "nameOnCard",
        rules: {
            ...rules.required("Name on Card"),
            ...rules.minLength("Name on Card", 2)
        }
    },
    {
        label: "Card Number",
        type: "text",
        name: "cardNumber",
        rules: {
            ...rules.required("Card Number")
        }
    },
    {
        label: "Expiration Date",
        type: "text",
        name: "expirationMonth",
        rules: {
            ...rules.required("Expiration Month")
        }
    },
    {
        type: "text",
        name: "expirationYear",
        rules: {
            ...rules.required("Expiration Year")
        }
    },
    {
        label: "CVV",
        type: "text",
        name: "cvv",
        rules: {
            ...rules.required("CVV")
        }
    }
];