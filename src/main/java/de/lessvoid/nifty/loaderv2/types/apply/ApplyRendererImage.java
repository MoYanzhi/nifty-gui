package de.lessvoid.nifty.loaderv2.types.apply;

import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.xml.xpp3.Attributes;

public class ApplyRendererImage implements ApplyRenderer {
  private Convert convert;

  public ApplyRendererImage(final Convert convertParam) {
    convert = convertParam;
  }

  public void apply(
      final Element element,
      final Attributes attributes,
      final NiftyRenderEngine renderEngine) {
    PanelRenderer panelRenderer = element.getRenderer(PanelRenderer.class);
    if (panelRenderer != null) {
      // panel renderer set means this is a panel and therefore the
      // image attributes here do not apply
      return;
    }
    ImageRenderer imageRenderer = element.getRenderer(ImageRenderer.class);
    if (imageRenderer == null) {
      return;
    }

    NiftyImage image =
      renderEngine.createImage(
          attributes.get("filename"),
          attributes.getAsBoolean("filter", Convert.DEFAULT_IMAGE_FILTER));
    if (image == null) {
      return;
    }
    imageRenderer.setImage(image);
    image.setImageMode(convert.imageMode(attributes.get("imageMode")));
    imageRenderer.setInset(convert.insetSizeValue(attributes.get("inset"), image.getHeight()));

    if (element.getConstraintWidth() == null) {
      element.setConstraintWidth(convert.sizeValue(image.getWidth() + "px"));
    }
    if (element.getConstraintHeight() == null) {
      element.setConstraintHeight(convert.sizeValue(image.getHeight() + "px"));
    }
  }
}