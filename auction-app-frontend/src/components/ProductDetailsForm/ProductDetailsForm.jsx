import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

import { FormContainer } from "src/components";

import { productDetailsFormFields } from "src/forms/fields";
import { getTopLevelCategories, getSubcategoriesByParentCategory } from "src/services";
import { BUTTON_LABELS, ROUTE_PATHS } from "src/constants";

import "./style.scss"

const ProductDetailsForm = ({ formData, setFormData }) => {
    const [categories, setCategories] = useState();
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [subcategories, setSubcategories] = useState([]);
    const [error, setError] = useState({});

    const navigate = useNavigate();

    const methods = useForm({
        mode: "onBlur",
        defaultValues: formData
    });

    const onSubmit = (data) => {
        setFormData(data);
        
        navigate("#prices");
    };

    const onCancel = () => {
        navigate(ROUTE_PATHS.MY_ACCOUNT);
    }

    const getCategories = async () => {
        try {
            const categoriesData = await getTopLevelCategories();
            const formattedCategories = categoriesData.map(category => ({
                value: category.id,
                label: category.name
            }));

            setCategories(formattedCategories);

            // fetch subcategories if form data has category
            if (formData.category) {
                setSelectedCategory(formData.category);
            }

        } catch (error) {
            setError(error.message);
        }
    }

    const fetchSubcategories = async (categoryId) => {
        try {
            const subcategoriesData = await getSubcategoriesByParentCategory(categoryId);
            const formattedSubcategories = subcategoriesData.map(subcategory => ({
                value: subcategory.id,
                label: subcategory.name
            }));
            
            setSubcategories(formattedSubcategories);
        } catch (error) {
            setError(error.message);
        }
    }

    useEffect(() => {
        getCategories();
    }, []);

    useEffect(() => {
        if (selectedCategory) {
            fetchSubcategories(selectedCategory);
        }
    }, [selectedCategory]);

    return (
        <div className="details-form form">
            <div className="form-header">
                <h5> ADD ITEM </h5>
            </div>
            <div className="form-fields">
                <FormContainer 
                    formFields={ productDetailsFormFields(categories, subcategories, setSelectedCategory) } 
                    onSubmit={ methods.handleSubmit(onSubmit) } 
                    onCancel={ onCancel }
                    buttonLabel={ BUTTON_LABELS.NEXT }
                    cancelLabel={ BUTTON_LABELS.CANCEL }
                    methods={ methods }
                />
            </div>
        </div>
    );
}

export default ProductDetailsForm;
