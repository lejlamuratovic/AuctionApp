import React from "react";
import "./aboutUs.scss";
import aboutus1 from "assets/aboutus1.png";
import aboutus2 from "assets/aboutus2.png";
import aboutus3 from "assets/aboutus3.png";

const AboutUs = () => {
  return (
    <div className="about-us">
      <div className="about-us-header">
        <h5>About Us</h5>
      </div>
      <div className="about-us-content">
        <div className="about-us-text body-regular">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            consequat pretium turpis, in eleifend mi laoreet sed. Donec ipsum
            mauris, venenatis sit amet porttitor id, laoreet eu magna. In
            convallis diam volutpat libero tincidunt semper. Ut aliquet erat
            rutrum, venenatis lacus ut, ornare lectus. Quisque congue ex sit
            amet diam malesuada, eget laoreet quam molestie. In id elementum
            turpis. Curabitur quis tincidunt mauris.
          </p>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            consequat pretium turpis, in eleifend mi laoreet sed. Donec ipsum
            mauris, venenatis sit amet porttitor id, laoreet eu magna. In
            convallis diam volutpat libero tincidunt semper. Ut aliquet erat
            rutrum, venenatis lacus ut, ornare lectus. Quisque congue ex sit
            amet diam malesuada, eget laoreet quam molestie. In id elementum
            turpis. Curabitur quis tincidunt mauris.
          </p>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            consequat pretium turpis, in eleifend mi laoreet sed. Donec ipsum
            mauris, venenatis sit amet porttitor id, laoreet eu magna. In
            convallis diam volutpat libero tincidunt semper. Ut aliquet erat
            rutrum, venenatis lacus ut, ornare lectus. Quisque congue ex sit
            amet diam malesuada, eget laoreet quam molestie. In id elementum
            turpis. Curabitur quis tincidunt mauris.
          </p>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            consequat pretium turpis, in eleifend mi laoreet sed. Donec ipsum
            mauris, venenatis sit amet porttitor id, laoreet eu magna. In
            convallis diam volutpat libero tincidunt semper. Ut aliquet erat
            rutrum, venenatis lacus ut, ornare lectus. Quisque congue ex sit
            amet diam malesuada, eget laoreet quam molestie. In id elementum
            turpis. Curabitur quis tincidunt mauris.
          </p>
        </div>
        <div className="about-us-images">
          <img src={aboutus1} alt="Description" />
          <img src={aboutus2} alt="Description" />
          <img src={aboutus3} alt="Description" />
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
