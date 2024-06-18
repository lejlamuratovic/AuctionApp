import { useState, useEffect } from "react";
import { useLocation, Link } from "react-router-dom";
import Modal from "react-modal";
import { FileUploader } from "react-drag-drop-files";

import { Button, ProfileTab, SellerTab, BidsTab, SettingsTab, ButtonLoadingIndicator } from "src/components";

import { MY_ACCOUNT_TABS, BUTTON_LABELS, BUTTON_VARIANTS, MY_ACCOUNT_TABS_MAP, ROUTE_PATHS, CSV_FILE_TYPES } from "src/constants";
import { close } from "src/assets/icons";
import { addProductUsingCsv } from "src/services/productService";

import "./style.scss";

const MyAccount = () => {
  const location = useLocation();

  const initialTab = location.hash ? location.hash.replace('#', '') : MY_ACCOUNT_TABS[0].id;

  const [activeTab, setActiveTab] = useState(initialTab);
  const [isCsvModalOpen, setIsCsvModalOpen] = useState(false);
  const [file, setFile] = useState(null);
  const [uploadLoading, setUploadLoading] = useState(false);
  const [uploadError, setUploadError] = useState(null);

  const appendHash = (newActiveTab) => {
    const hash = location.hash.replace('#', '');

    if (!hash || hash !== newActiveTab) {
      window.location.hash = newActiveTab;
    }
  };

  useEffect(() => {
    appendHash(activeTab); 
  }, []);

  const changeActiveTab = (tabId) => () => {
    setActiveTab(tabId);
    appendHash(tabId);
  };

  const renderActiveTabContent = () => {
    switch (activeTab) {
      case MY_ACCOUNT_TABS_MAP.PROFILE:
        return <ProfileTab />;
      case MY_ACCOUNT_TABS_MAP.SELLER: 
        return <SellerTab />;
      case MY_ACCOUNT_TABS_MAP.BIDS:
        return <BidsTab />;
      case MY_ACCOUNT_TABS_MAP.SETTINGS:
        return <SettingsTab />;
      default:
        return null;
    }
  };

  const handleFileChange = (file) => {
      setFile(file);
  }  

  const handleFileUpload = () => {
    const formData = new FormData();

    formData.append("file", file);

    setUploadLoading(true);

    addProductUsingCsv(formData)
      .then(() => {
        closeCsvModal();
      }).catch((error) => {
        setUploadError(error.response?.data?.message);
      }).finally(() => {
        setUploadLoading(false);
      });
  }

  const openCsvModal = () => {
    setIsCsvModalOpen(true);
  }

  const closeCsvModal = () => {
    setIsCsvModalOpen(false);
  }

  return (
    <div className="my-account-options-container">
      <div className="my-account-tabs">
        <div className="tab-options">
          { MY_ACCOUNT_TABS.map((tab) => (
            <span
              key={ tab.id }
              onClick={ changeActiveTab(tab.id) }
              className={ `${ activeTab === tab.id ? "active" : "inactive" } tab body-semibold` }
            >
              <img 
                  src={ activeTab === tab.id ? tab.activeIcon : tab.icon } 
                  alt={ `${ tab.label } icon` } 
                  className="tab-icon" 
              />
              { tab.label }
            </span> 
          )) }
        </div>
        { isCsvModalOpen &&
          <Modal
            isOpen={ isCsvModalOpen }
            onRequestClose={ closeCsvModal }
            className="csv-modal"
            overlayClassName="csv-modal-overlay"
            appElement={ document.getElementById('root') }
          >
            <div className="csv-modal-content">
              <img src = { close } alt="Close" className="close-icon" onClick={ closeCsvModal } />
              <span className="body-semibold">Upload CSV File</span>
              { uploadError && <span className="error-message body-bold">{ uploadError }</span> }
              <FileUploader
                  handleChange={ handleFileChange }
                  name="csvFile"
                  multiple={ false }
                  classes="file-uploader"
                  types={ CSV_FILE_TYPES }
              />
              <Button 
                  label={ uploadLoading ? <ButtonLoadingIndicator /> : BUTTON_LABELS.UPLOAD }
                  variant={ BUTTON_VARIANTS.OUTLINED } 
                  onButtonClick={ handleFileUpload }
              />
            </div>
          </Modal>
        }
        <div className="add-item-button">
          <Link to={ ROUTE_PATHS.ADD_ITEM }>
            <Button label={ BUTTON_LABELS.ADD_ITEM } variant= { BUTTON_VARIANTS.FILLED } />
          </Link>
          <span 
            className="body-regular"
            onClick={ openCsvModal }
          >
              Or add using CSV
          </span>
        </div>
      </div>
      <div>
        { renderActiveTabContent() }
      </div>
    </div>
  )
}

export default MyAccount;
