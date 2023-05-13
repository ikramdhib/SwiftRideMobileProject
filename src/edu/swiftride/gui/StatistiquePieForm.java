package edu.swiftride.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import edu.swiftride.entities.Avis;
import edu.swiftride.services.AvisService;
import java.util.ArrayList;

public class StatistiquePieForm extends Form {
    
    ArrayList<Avis> avisList;

    public float calcul_nbr_etoile(ArrayList<Avis> avis, int etoile) {       
        int f = 0;
        for(int i=0; i<avis.size(); i++) {
            if (avis.get(i).getEtoile() == etoile) {
                f++;
            }
        }
        return f;
    }
    
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(70);
        renderer.setLegendTextSize(70);
        renderer.setMargins(new int[]{12, 14, 11, 10, 60, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        series.add("1 Etoile", this.calcul_nbr_etoile(avisList, 1));
        series.add("2 Etoiles", this.calcul_nbr_etoile(avisList, 2));
        series.add("3 Etoiles", this.calcul_nbr_etoile(avisList, 3));
        series.add("4 Etoiles", this.calcul_nbr_etoile(avisList, 4));
        series.add("5 Etoiles", this.calcul_nbr_etoile(avisList, 5));
        return series;
    }

public void createPieChartForm() {     
    setTitle("Statistiques Avis");

    // If avisList is null, fetch the data from the database
    if (avisList == null) {
        avisList = AvisService.getInstance().getAllAvis();
    }

    // Generate the values
    double[] values = new double[]{
        this.calcul_nbr_etoile(avisList, 1),
        this.calcul_nbr_etoile(avisList, 2),
        this.calcul_nbr_etoile(avisList, 3),
        this.calcul_nbr_etoile(avisList, 4),
        this.calcul_nbr_etoile(avisList, 5)
    };
        
    // Set up the renderer
    int[] colors = new int[]{ColorUtil.GRAY, ColorUtil.GREEN, ColorUtil.rgb(255, 0, 255), ColorUtil.BLUE, ColorUtil.BLACK};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);

    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Calandrier_ex", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    // Create a container with a BorderLayout and add the chart to the center
    Container container = new Container(new BorderLayout());
    container.add(BorderLayout.CENTER, c);

    // Add the container to the form
    add(container);
    Toolbar toolbar = new Toolbar();
setToolbar(toolbar);

// Add a command to the toolbar that takes you to the home page
toolbar.addCommandToSideMenu("Home", FontImage.createMaterial(FontImage.MATERIAL_HOME, toolbar.getUnselectedStyle()), e -> {
    new Home().show();
});
toolbar.addCommandToSideMenu("Show Avis", FontImage.createMaterial(FontImage.MATERIAL_HOME, toolbar.getUnselectedStyle()), e -> {
                ShowAvisForm f = new ShowAvisForm();
            f.show();
});
}
}