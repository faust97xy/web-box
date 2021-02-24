package pcshaman.web.core.model.dao;

import java.util.List;
import pcshaman.web.core.model.entities.SettingsEntity;
import pcshaman.web.core.model.repo.SettingsRepository;
import pcshaman.web.core.util.object.ObjectTool;

/**
 *
 * @author jjurczak
 * @param <E>
 */
public abstract class SettingsDAO<E extends SettingsEntity> extends BasicDAO<E> {

    @Override
    protected abstract SettingsRepository<E> getRepository();

    public List<E> getSettings4Group(String groupe) {
        return getRepository().getSettings4Group(groupe);
    }

    public E getSettings4Name(String name) {
        return getRepository().getSettings4Name(name);
    }

    protected String getValue4Name(String name) {
        String value = null;
        if (ObjectTool.exist(name)) {
            E setting = getSettings4Name(name);
            if (ObjectTool.exist(setting)) {
                value = setting.getValue();
            }
        }
        return value;
    }
}