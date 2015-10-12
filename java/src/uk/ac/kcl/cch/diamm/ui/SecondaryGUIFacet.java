package uk.ac.kcl.cch.diamm.ui;

import uk.ac.kcl.cch.diamm.facet.*;
import uk.ac.kcl.cch.diamm.facet.type.*;
import uk.ac.kcl.cch.facet.Facet;
import uk.ac.kcl.cch.facet.ui.FacetCriterion;
import uk.ac.kcl.cch.facet.ui.GUIFacet;
import uk.ac.kcl.cch.facet.ui.GUIFacetManager;
import uk.ac.kcl.cch.facet.ui.GUIFacetState;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Elliott Hall
 * Date: 15-Nov-2010
 * Time: 12:17:37
 * To change this template use File | Settings | File Templates.
 */
public class SecondaryGUIFacet extends GUIFacet {

    public SecondaryGUIFacet(Facet f, GUIFacetManager g) {
        super(f, g);
    }

    protected void addFacetData(HttpServletRequest request) {
        SecondaryFacet sf = (SecondaryFacet) getFacet();
        ArrayList<Facet> seconds = sf.getSeconds();
        if (getGUIstate() == GUIFacetState.OPEN) {
            for (int i = 0; i < seconds.size(); i++) {
                Facet f = seconds.get(i);
                if (f.getType().getClass() == ProvenanceFacetType.class) {
                    ProvenanceFacet pf = (ProvenanceFacet) f;
                    //request.setAttribute(Constants.provenanceAttrName, getDynamicCriteria(pf.provenanceValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.provenanceAttrSelected, pf.getValue());
                        request.setAttribute("alprovenance",pf.getCurrentCriterion().getLabel());
                    }
                } else if (f.getType().getClass() == PersonFacetType.class) {
                    PersonFacet pf = (PersonFacet) f;
                    //request.setAttribute(Constants.personAttrName, getDynamicCriteria(pf.personValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.personAttrSelected, pf.getValue());
                        request.setAttribute("person",pf.getCurrentCriterion().getLabel());
                    }
                } else if (f.getType().getClass() == LanguageFacetType.class) {
                    LanguageFacet pf = (LanguageFacet) f;
                    //request.setAttribute(Constants.languageAttrName, getDynamicCriteria(pf.languageValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.languageAttrSelected, pf.getValue());
                        request.setAttribute("language",pf.getCurrentCriterion().getLabel());
                    }
                /*} else if (f.getType().getClass() == NotationFacetType.class) {
                    NotationFacet pf = (NotationFacet) f;
                    //request.setAttribute(Constants.notationAttrName, getDynamicCriteria(pf.notationValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.notationAttrSelected, pf.getValue());
                    }
                } else if (f.getType().getClass() == SetFacetType.class) {
                    SetFacet pf = (SetFacet) f;
                    //request.setAttribute(Constants.setAttrName, getDynamicCriteria(pf.setValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.setAttrSelected, pf.getValue());
                    }*/
                } else if (f.getType().getClass() == ClefFacetType.class) {
                    ClefFacet pf = (ClefFacet) f;
                    //request.setAttribute(Constants.clefAttrName, getDynamicCriteria(pf.clefValues));
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.clefAttrSelected, pf.getValue());
                        request.setAttribute("clef",pf.getCurrentCriterion().getLabel());
                    }
                } else if (f.getType().getClass() == CenturyFacetType.class) {
                    CenturyFacet pf = (CenturyFacet) f;
                    request.setAttribute("centuries", CenturyFacet.centuries);
                    if (pf.getValue() > 0) {
                        request.setAttribute(Constants.century1AttrSelected, pf.getValue());
                        if (pf.getCentury2() > 0) {
                            request.setAttribute(Constants.century2AttrSelected, pf.getCentury2());
                        }
                    }
                }
            }
        }
        //Label
        if (getLabel().length()>0){
            request.setAttribute("secondaryLabel", getLabel());
        }
    }

    public String getDescription() {
        //Country selected, acts like normal facet at top level
        SecondaryFacet sf = (SecondaryFacet) getFacet();
        ArrayList<Facet> sc = sf.getSeconds();
        String desc = "";
        for (int i = 0; i < sc.size(); i++) {
            Facet f = sc.get(i);
            if (f.getValue() > 0) {
                FacetCriterion fc = f.getCurrentCriterion();
                if (fc != null) {
                    String label = "";
                    if (ProvenanceFacetType.class == f.getType().getClass()) {
                        label = "Provenance";
                    } else if (NotationFacetType.class == f.getType().getClass()) {
                        label = "Notation";
                    } else if (LanguageFacetType.class == f.getType().getClass()) {
                        label = "Language";
                    } else if (SetFacetType.class == f.getType().getClass()) {
                        label = "Set";
                    } else if (CenturyFacetType.class == f.getType().getClass()) {
                        label = "Century";
                    } else if (PersonFacetType.class == f.getType().getClass()) {
                        label = "Person";
                    } else if (ClefFacetType.class == f.getType().getClass()) {
                        label = "Clef";
                    }
                    desc = " <li> <label> " + label + ":" + fc.getLabel() + "</label>\n" +
                            "<a class=\"t9 m3\" href=\"FacetManager?op=4&FacetType=SECONDARYFACET\">Remove</a></li>";
                }
            }
        }
        return desc;
    }

    public String getFacetType() {
        return getFacet().getType().getTypeName();
    }

    public String getLabel() {
        SecondaryFacet sf = (SecondaryFacet) getFacet();
        ArrayList<Facet> seconds = sf.getSeconds();
        String label = "";
        for (int i = 0; i < seconds.size(); i++) {
            Facet f = seconds.get(i);
            if (f.getType().getClass() == ProvenanceFacetType.class) {
                ProvenanceFacet pf = (ProvenanceFacet) f;
                if (pf.getValue() > 0&&pf.getCurrentCriterion()!=null) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == PersonFacetType.class) {
                PersonFacet pf = (PersonFacet) f;

                if (pf.getValue() > 0) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == LanguageFacetType.class) {
                LanguageFacet pf = (LanguageFacet) f;

                if (pf.getValue() > 0) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == NotationFacetType.class) {
                NotationFacet pf = (NotationFacet) f;

                if (pf.getValue() > 0) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == SetFacetType.class) {
                SetFacet pf = (SetFacet) f;

                if (pf.getValue() > 0) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == ClefFacetType.class) {
                ClefFacet pf = (ClefFacet) f;
                if (pf.getValue() > 0) {
                    label=pf.getCurrentCriterion().getLabel();
                }
            } else if (f.getType().getClass() == CenturyFacetType.class) {
                CenturyFacet pf = (CenturyFacet) f;
                if (pf.getValue() > 0) {
                    label=pf.getValue()+"th";
                    if (pf.getCentury2() > 0) {
                        label+="-"+pf.getCentury2()+"th";
                    }
                }
            }
        }

        return label;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean updateFacetFromRequest(HttpServletRequest request) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
