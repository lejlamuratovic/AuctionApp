import { rules } from "src/forms/rules";

export const productDetailsFormFields = [
  {
    label: "What do you sell?",
    type: "text",
    name: "productName",
    rules: {
      ...rules.required("Product name"),
      ...rules.minLength(2, "Product name"),
      ...rules.maxLength(100, "Product name")
    }
  },
  {
    label: "Description",
    type: "textarea",
    name: "description",
    rules: {
      ...rules.required("Description"),
      ...rules.minLength(10, "Description"),
      ...rules.maxLength(700, "Description")
    }
  },
  {
    label: "Files",
    type: "file",
    name: "file",
    rules: {
      ...rules.required("File")
    }
  }
];
