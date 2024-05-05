import { rules } from "src/forms/rules";

export const productPriceFormFields = [
    {
        label: "Your start price",
        type: "number",
        name: "startPrice",
        step: "0.01",
        rules: {
            ...rules.required("Start price"),
            ...rules.minValue(0.01, "Start price")
        }
    }, 
    {
        label: "Start date",
        type: "date",
        name: "startDate",
        rules: {
            ...rules.required("Start date")
        }
    },
    {
        label: "End date",
        type: "date",
        name: "endDate",
        rules: {
            ...rules.required("End date")
        }
    }
];
