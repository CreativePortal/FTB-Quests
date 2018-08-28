package com.feed_the_beast.ftbquests.net;

import com.feed_the_beast.ftblib.lib.client.ClientUtils;
import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.ftbquests.client.ClientQuestFile;
import com.feed_the_beast.ftbquests.gui.GuiQuest;
import com.feed_the_beast.ftbquests.gui.GuiQuestChest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LatvianModder
 */
public class MessageClaimRewardResponse extends MessageToClient
{
	private int uid;

	public MessageClaimRewardResponse()
	{
	}

	public MessageClaimRewardResponse(int id)
	{
		uid = id;
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBQuestsNetHandler.GENERAL;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeInt(uid);
	}

	@Override
	public void readData(DataIn data)
	{
		uid = data.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onMessage()
	{
		if (ClientQuestFile.existsWithTeam())
		{
			ClientQuestFile.INSTANCE.rewards.add(uid);

			GuiQuest guiQuest = ClientUtils.getCurrentGuiAs(GuiQuest.class);

			if (guiQuest != null)
			{
				guiQuest.rewards.refreshWidgets();
			}
			else
			{
				GuiQuestChest guiChest = ClientUtils.getCurrentGuiAs(GuiQuestChest.class);

				if (guiChest != null)
				{
					guiChest.updateRewards();
				}
			}
		}
	}
}