package com.chernobyl.gameengine.layer;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;

public class LayerStack implements Iterable<Layer>{

    public int begin() { return 0; }
    public int end() { return m_Layers.size() - 1; }
    private final Vector<Layer> m_Layers;
    private int m_LayerInsertIndex;
    private static final int LAYER_SIZE = 10;


    public LayerStack()
    {
        m_Layers = new Vector<>(LAYER_SIZE);
        m_LayerInsertIndex = 0;
    }

    public void PushLayer(Layer layer)
    {
        if(m_Layers.size() == 0) m_Layers.addElement(layer);
        else m_Layers.insertElementAt(layer, m_LayerInsertIndex);
        layer.OnAttach();
        m_LayerInsertIndex++;
    }

    public void PushOverlay(Layer overlay)
    {
        m_Layers.addElement(overlay);
        overlay.OnAttach();
    }

    public void PopLayer(Layer layer)
    {
        int it = m_Layers.indexOf(layer);
        if (it != end())
        {
            layer.OnDetach();
            boolean success = m_Layers.remove(layer);
            HB_CORE_ASSERT(success, "Could not remove layer");
            m_LayerInsertIndex--;
        }
    }

    public void PopOverlay(Layer overlay)
    {
        int it = m_Layers.lastIndexOf(overlay);
        if (it != end()) {
            overlay.OnDetach();
            m_Layers.remove(it);
        }
    }

    public Layer get(int index) {
        return m_Layers.get(index);
    }

    @Override
    public Iterator<Layer> iterator() {
        return m_Layers.iterator();
    }

    @Override
    public void forEach(Consumer<? super Layer> action) {
        m_Layers.forEach(action);
    }

    @Override
    public Spliterator<Layer> spliterator() {
        return m_Layers.spliterator();
    }
}
