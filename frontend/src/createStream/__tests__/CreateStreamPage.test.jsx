import React from "react";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import { describe, expect, it } from "@jest/globals";
import CreateStreamPage from "../CreateStreamPage";
import Page from "../../general/Page";
import CreateStreamPageContents from "../CreateStreamPageContents";

Enzyme.configure({ adapter: new Adapter() });

describe("<CreateStreamPage/> functional Component", () => {
  const wrapper = Enzyme.shallow(<CreateStreamPage />);

  describe("returns a component that", () => {
    it("Contains 1 <Page/> component with expected props", () => {
      const expectedTitle = "Create a Stream";
      const expectedCrumb = [
        ["Home", "/Home"],
        ["Active Streams", "/Streams"],
        ["New Stream", "/Streams/New"]
      ];

      expect(wrapper.find(Page)).toHaveLength(1);

      const page = wrapper.find(Page).first();
      expect(page.props().title).toBe(expectedTitle);
      expect(page.props().breadcrumbs).toStrictEqual(expectedCrumb);
    });
    it("Contains 1 <CreateStreamPageContents/> component with expected props", () => {
      expect(wrapper.find(CreateStreamPageContents)).toHaveLength(1);
    });
  });
});
