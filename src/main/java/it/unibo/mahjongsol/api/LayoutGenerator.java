package it.unibo.mahjongsol.api;

import java.util.List;

/**
 * Genera la disposizione spaziale iniziale delle tessere.
 */
public interface LayoutGenerator {

  /**
   * Calcola e restituisce tutte le coordinate 3D in cui deve essere
   * posizionata una tessera per il layout scelto.
   * 
   * @return Una lista di posizioni valide.
   */
  List<Position> generateLayout();
}